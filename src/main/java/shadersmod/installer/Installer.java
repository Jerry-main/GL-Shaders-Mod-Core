package shadersmod.installer;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import shadersmod.client.ShadersTex;
import shadersmod.common.SMCVersion;
import shadersmod.installer.Json;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Installer.class */
public class Installer {
    MessageDigest mdSha;
    StringBuilder logMessage = new StringBuilder();
    static final String mcVer = "1.12.2";
    static final String modVer = "2.7.1";
    static final String friendlyModName = "ShadersMod";
    static final String libraryModDir = "shadersmodcore";
    static final String libraryModName = "ShadersModCore";
    static final String libraryModVer = "2.7.1mc1.12.2";
    static final String libraryModDesc = "shadersmodcore:ShadersModCore:2.7.1mc1.12.2";
    static final String versionIn = "1.12.2";
    static final String versionMod = "1.12.2-ShadersMod2.7.1";
    static final String profileMod = "1.12.2-ShadersMod";

    public static void main(String[] args) {
        Installer inst = new Installer();
        inst.run();
    }

    public void run() {
        try {
            this.mdSha = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No SHA algorithm");
            this.mdSha = null;
        }
        File mcDir = getMcDir();
        String[] vers = new File(mcDir, "versions").list(new FilenameFilter() { // from class: shadersmod.installer.Installer.1
            @Override // java.io.FilenameFilter
            public boolean accept(File dir, String name) {
                return name.equals(SMCVersion.mcVersion) && name.indexOf(Installer.friendlyModName) == -1 && new File(dir, name).isDirectory();
            }
        });
        String message = String.format("Minecraft directory %s\n\nSelect base version.", mcDir.getAbsolutePath());
        int option = JOptionPane.showConfirmDialog((Component) null, "Make sure you have closed Minecraft and Minecraft Launcher", "ShadersMod 2.7.1 Installer for Minecraft 1.12.2", 2, -1);
        if (option == 0) {
            Object baseVer = JOptionPane.showInputDialog((Component) null, message, "ShadersMod 2.7.1 Installer for Minecraft 1.12.2", -1, (Icon) null, vers, SMCVersion.mcVersion);
            if (baseVer instanceof String) {
                startInstall(mcDir, (String) baseVer);
            }
        }
    }

    public File getMcDir() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home", ".");
        if (osName.contains("win")) {
            String baseDir = System.getenv("APPDATA");
            if (baseDir == null) {
                baseDir = userHome;
            }
            return new File(baseDir, ".minecraft/");
        }
        if (osName.contains("mac")) {
            return new File(userHome, "Library/Application Support/minecraft");
        }
        if (osName.contains("linux") || osName.contains("unix")) {
            return new File(userHome, ".minecraft/");
        }
        return new File(userHome, "minecraft/");
    }

    public void startInstall(File mcDir, String baseVer) {
        boolean result = false;
        if (installLibraries(mcDir)) {
            String versionMod2 = baseVer + "-ShadersMod2.7.1";
            if (installVersion(mcDir, baseVer, versionMod2) && installProfile(mcDir, profileMod, versionMod2)) {
                result = true;
            }
        }
        this.logMessage.append(result ? "complete" : "fail");
        JOptionPane.showMessageDialog((Component) null, this.logMessage, "Result", -1);
    }

    public void startInstall(File mcDir) {
        boolean result = false;
        if (installLibraries(mcDir) && installVersion(mcDir, SMCVersion.mcVersion, versionMod) && installProfile(mcDir, profileMod, versionMod)) {
            result = true;
        }
        this.logMessage.append(result ? "complete" : "fail");
        JOptionPane.showMessageDialog((Component) null, this.logMessage, "Result", -1);
    }

    void close(Closeable c) throws IOException {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    int readLoop(InputStream inp, byte[] buffer, int size) throws IOException {
        int pos;
        int n;
        int i = 0;
        while (true) {
            pos = i;
            if (pos >= size || (n = inp.read(buffer, pos, size - pos)) <= 0) {
                break;
            }
            i = pos + n;
        }
        return pos;
    }

    byte[] readToByteArray(InputStream inp) throws IOException {
        int pos;
        int nread;
        ArrayList<byte[]> buffers = new ArrayList<>();
        int bufIndex = 0;
        do {
            byte[] buf = new byte[ShadersTex.initialBufferSize];
            buffers.add(buf);
            pos = 0;
            do {
                nread = inp.read(buf, pos, ShadersTex.initialBufferSize - pos);
                if (nread <= 0) {
                    break;
                }
                pos += nread;
            } while (pos < 1048576);
            if (nread <= 0) {
                break;
            }
            bufIndex++;
        } while (nread > 0);
        byte[] abyte = new byte[(bufIndex * ShadersTex.initialBufferSize) + pos];
        int i = 0;
        while (i < bufIndex) {
            System.arraycopy(buffers.get(i), 0, abyte, i * ShadersTex.initialBufferSize, ShadersTex.initialBufferSize);
            i++;
        }
        System.arraycopy(buffers.get(i), 0, abyte, i * ShadersTex.initialBufferSize, pos);
        return abyte;
    }

    byte[] getResourceByteArray(String name) {
        return readByteArray(getClass().getResourceAsStream(name));
    }

    byte[] readByteArray(InputStream inp) throws IOException {
        int nread;
        byte[] abyte = null;
        if (inp != null) {
            ArrayList<byte[]> buffers = new ArrayList<>();
            int bufIndex = 0;
            int pos = 0;
            do {
                try {
                    bufIndex = buffers.size();
                    byte[] buf = new byte[ShadersTex.initialBufferSize];
                    buffers.add(buf);
                    pos = 0;
                    do {
                        nread = inp.read(buf, pos, ShadersTex.initialBufferSize - pos);
                        if (nread <= 0) {
                            break;
                        }
                        pos += nread;
                    } while (pos < 1048576);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (nread > 0);
            close(inp);
            abyte = new byte[(bufIndex * ShadersTex.initialBufferSize) + pos];
            int i = 0;
            while (i < bufIndex) {
                System.arraycopy(buffers.get(i), 0, abyte, i * ShadersTex.initialBufferSize, ShadersTex.initialBufferSize);
                i++;
            }
            System.arraycopy(buffers.get(i), 0, abyte, i * ShadersTex.initialBufferSize, pos);
        }
        return abyte;
    }

    String getResourceString(String resName) throws IOException {
        InputStream in = getClass().getResourceAsStream(resName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        try {
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public boolean writeFileWithSha(File dst, byte[] abyte) throws IOException {
        boolean result = false;
        OutputStream out = null;
        Writer wr = null;
        try {
            OutputStream out2 = new FileOutputStream(dst);
            out2.write(abyte);
            out2.close();
            out = null;
            result = true;
            Writer wr2 = new FileWriter(dst.getPath() + ".sha");
            wr2.write(toShaString(abyte));
            wr2.close();
            wr = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        close(out);
        close(wr);
        return result;
    }

    public boolean writeFile(File dst, byte[] abyte) throws IOException {
        boolean result = false;
        OutputStream out = null;
        try {
            OutputStream out2 = new FileOutputStream(dst);
            out2.write(abyte);
            out2.close();
            out = null;
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        close(out);
        return result;
    }

    public void copyFile(File src, File dst) throws IOException {
        InputStream inp = null;
        OutputStream out = null;
        try {
            int size = (int) src.length();
            byte[] abyte = new byte[size];
            InputStream inp2 = new FileInputStream(src);
            readLoop(inp2, abyte, size);
            inp2.close();
            inp = null;
            OutputStream out2 = new FileOutputStream(dst);
            out2.write(abyte);
            out2.close();
            out = null;
            dst.setLastModified(src.lastModified());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        close(inp);
        close(out);
    }

    public boolean copyFileWithSha(File src, File dst) throws IOException {
        boolean result = false;
        InputStream inp = null;
        OutputStream out = null;
        Writer wr = null;
        try {
            int size = (int) src.length();
            byte[] abyte = new byte[size];
            InputStream inp2 = new FileInputStream(src);
            readLoop(inp2, abyte, size);
            inp2.close();
            inp = null;
            OutputStream out2 = new FileOutputStream(dst);
            out2.write(abyte);
            out2.close();
            out = null;
            dst.setLastModified(src.lastModified());
            result = true;
            Writer wr2 = new FileWriter(dst.getPath() + ".sha");
            wr2.write(toShaString(abyte));
            wr2.close();
            wr = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        close(inp);
        close(out);
        close(wr);
        return result;
    }

    public String toShaString(byte[] abyte) {
        this.mdSha.reset();
        byte[] sha = this.mdSha.digest(abyte);
        StringBuilder sb = new StringBuilder();
        for (byte b : sha) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }

    private InputStream getJarAsStream() throws IOException {
        String classUrlStr;
        int endIndex;
        URL classUrl = getClass().getClassLoader().getResource(getClass().getName().replace('.', '/') + ".class");
        if (classUrl != null && (endIndex = (classUrlStr = classUrl.toString()).indexOf("!/")) != -1 && classUrlStr.startsWith("jar:")) {
            try {
                URL jarUrl = new URL(classUrlStr.substring(4, endIndex));
                InputStream inputStream = jarUrl.openStream();
                return inputStream;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public boolean installSelfLibrary(File mcDir, String subDir, String name, String version) throws IOException {
        boolean result = false;
        File outDir = new File(mcDir, "libraries/" + subDir + "/" + name + "/" + version);
        File outJar = new File(outDir, name + "-" + version + ".jar");
        outDir.mkdirs();
        InputStream inputStream = getJarAsStream();
        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream("/" + name + "-" + version + ".jar");
        }
        if (inputStream != null) {
        }
        byte[] abyte = readByteArray(inputStream);
        if (abyte != null && writeFileWithSha(outJar, abyte)) {
            result = true;
        }
        return result;
    }

    public boolean installLibrary(File mcDir, String subDir, String name, String version) throws IOException {
        boolean result = false;
        File outDir = new File(mcDir, "libraries/" + subDir + "/" + name + "/" + version);
        File outJar = new File(outDir, name + "-" + version + ".jar");
        outDir.mkdirs();
        byte[] abyte = readByteArray(getClass().getResourceAsStream("/" + name + "-" + version + ".jar"));
        if (abyte != null && writeFileWithSha(outJar, abyte)) {
            result = true;
        }
        return result;
    }

    public boolean installLibraries(File mcDir) {
        if (installSelfLibrary(mcDir, libraryModDir, libraryModName, libraryModVer)) {
            return true;
        }
        return false;
    }

    public void showTextResource(String resName, String title) throws IOException {
        InputStream in = getClass().getResourceAsStream(resName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder text = new StringBuilder();
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    text.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            reader.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        JOptionPane.showMessageDialog((Component) null, text, title, -1);
    }

    Json.JsObject libraryAsJso(String library) {
        Json.JsObject jso = new Json.JsObject();
        jso.put("name", new Json.JsString(library));
        return jso;
    }

    public boolean writeVersionJson(File inpJson, File outJson, String outVersion) throws IOException {
        boolean result = false;
        BufferedReader inr = null;
        try {
            BufferedReader inr2 = new BufferedReader(new FileReader(inpJson));
            JsonParser jp = new JsonParser(inr2);
            Json.JsObject jso = jp.getObject();
            inr2.close();
            inr = null;
            jso.put("id", new Json.JsString(outVersion));
            jso.put("mainClass", new Json.JsString("net.minecraft.launchwrapper.Launch"));
            Json.JsString jssArg = (Json.JsString) jso.get("minecraftArguments");
            jssArg.str += " --tweakClass shadersmod.launch.SMCTweaker";
            Json.JsArray jsaLibs = (Json.JsArray) jso.get("libraries");
            ArrayList<Json.JsValue> ao1 = jsaLibs.elements;
            ArrayList<Json.JsValue> ao2 = new ArrayList<>();
            jsaLibs.elements = ao2;
            ao2.add(libraryAsJso(libraryModDesc));
            ao2.add(libraryAsJso("net.minecraft:launchwrapper:1.12.2"));
            ao2.add(libraryAsJso("org.ow2.asm:asm-all:5.2"));
            int max = ao1.size();
            for (int i = 0; i < max; i++) {
                Json.JsObject jsoLib = (Json.JsObject) ao1.get(i);
                Json.JsString jssLibName = (Json.JsString) jsoLib.get("name");
                String libName = jssLibName.str;
                if (libName != null && !libName.startsWith("shadersmodcore:ShadersModCore:") && !libName.startsWith("net.minecraft:launchwrapper:") && !libName.startsWith("org.ow2.asm:asm-all:")) {
                    ao2.add(jsoLib);
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(baos);
            jso.print(pw, 0);
            pw.close();
            byte[] abyte = baos.toByteArray();
            if (writeFileWithSha(outJson, abyte)) {
            }
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        close(inr);
        return result;
    }

    public boolean createVersionJson(File inpJson, File outJson, String outVersion) throws IOException {
        boolean result = false;
        BufferedReader inr = null;
        try {
            BufferedReader inr2 = new BufferedReader(new FileReader(inpJson));
            JsonParser jp = new JsonParser(inr2);
            jp.getObject();
            Json.JsObject jso = new Json.JsObject();
            inr2.close();
            inr = null;
            jso.put("id", new Json.JsString(outVersion));
            String time = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            jso.put("time", new Json.JsString(time));
            jso.put("type", new Json.JsString("release"));
            jso.put("releaseTime", new Json.JsString(getResourceString("/shadersmod/installer/releaseTime")));
            jso.put("inheritsFrom", new Json.JsString(SMCVersion.mcVersion));
            jso.put("mainClass", new Json.JsString("net.minecraft.launchwrapper.Launch"));
            jso.put("minecraftArguments", new Json.JsString("--username ${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"));
            Json.JsString jssArg = (Json.JsString) jso.get("minecraftArguments");
            jssArg.str += " --tweakClass shadersmod.launch.SMCTweaker";
            jso.put("libraries", new Json.JsArray());
            ArrayList<Json.JsValue> ao = ((Json.JsArray) jso.get("libraries")).elements;
            ao.add(libraryAsJso(libraryModDesc));
            ao.add(libraryAsJso("net.minecraft:launchwrapper:1.12.2"));
            ao.add(libraryAsJso("org.ow2.asm:asm-all:5.2"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(baos);
            jso.print(pw, 0);
            pw.close();
            byte[] abyte = baos.toByteArray();
            if (writeFileWithSha(outJson, abyte)) {
            }
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        close(inr);
        return result;
    }

    public boolean installVersion(File mcDir, String inpVersion, String outVersion) {
        File inpDir = new File(mcDir, "versions/" + inpVersion + "/");
        File inpJar = new File(inpDir, inpVersion + ".jar");
        File inpJson = new File(inpDir, inpVersion + ".json");
        File outDir = new File(mcDir, "versions/" + outVersion + "/");
        File outJar = new File(outDir, outVersion + ".jar");
        File outJson = new File(outDir, outVersion + ".json");
        if (inpJar.exists() && inpJson.exists()) {
            outDir.mkdirs();
            if (copyFileWithSha(inpJar, outJar) && createVersionJson(inpJson, outJson, outVersion)) {
                String str = String.format("Version %s => %s\n", inpVersion, outVersion);
                this.logMessage.append(str);
                return true;
            }
            return false;
        }
        String str2 = String.format("Cannot find version %s .. skip\n", inpVersion);
        this.logMessage.append(str2);
        return false;
    }

    public boolean installProfile(File mcDir, String profile, String version) throws IOException {
        boolean result = false;
        File inpJson = new File(mcDir, "launcher_profiles.json");
        if (inpJson.exists()) {
            BufferedReader inr = null;
            try {
                BufferedReader inr2 = new BufferedReader(new FileReader(inpJson));
                JsonParser jp = new JsonParser(inr2);
                Json.JsObject jsoRoot = jp.getObject();
                inr2.close();
                inr = null;
                Json.JsObject jsoProfiles = (Json.JsObject) jsoRoot.get("profiles");
                Json.JsObject jsoModProfile = (Json.JsObject) jsoProfiles.get(profile);
                if (jsoModProfile == null) {
                    Json.JsObject jsoModProfile2 = new Json.JsObject();
                    jsoModProfile2.put("name", new Json.JsString(profile));
                    jsoModProfile2.put("lastVersionId", new Json.JsString(version));
                    jsoModProfile2.put("useHopperCrashService", new Json.JsBoolean("false"));
                    jsoModProfile2.put("launcherVisibilityOnGameClose", new Json.JsString("keep the launcher open"));
                    jsoProfiles.put(profile, jsoModProfile2);
                } else {
                    jsoModProfile.put("name", new Json.JsString(profile));
                    jsoModProfile.put("lastVersionId", new Json.JsString(version));
                }
                jsoRoot.put("selectedProfile", new Json.JsString(profile));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintWriter pw = new PrintWriter(baos);
                jsoRoot.print(pw, 0);
                pw.close();
                byte[] abyte = baos.toByteArray();
                if (writeFile(inpJson, abyte)) {
                }
                result = true;
                this.logMessage.append(String.format("Add profile %s for version %s\n", profile, version));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            close(inr);
        }
        return result;
    }
}
