package shadersmod.transform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import shadersmod.transform.Names;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/NamerObf.class */
public class NamerObf extends NamerSrg {
    ArrayList<String> obfNames;
    int obfNamesIndex;

    @Override // shadersmod.transform.Namer
    public void beginSetNames() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("shadersmod/transform/obfnames.txt"), StandardCharsets.UTF_8));
        this.obfNames = new ArrayList<>();
        while (true) {
            try {
                String s = reader.readLine();
                if (s == null) {
                    break;
                } else {
                    this.obfNames.add(s);
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
        this.obfNamesIndex = 0;
    }

    @Override // shadersmod.transform.Namer
    public void endSetNames() {
        this.obfNames = null;
    }

    @Override // shadersmod.transform.Namer
    /* renamed from: c */
    Names.Clas mo15c(String name) {
        ArrayList<String> arrayList = this.obfNames;
        int i = this.obfNamesIndex;
        this.obfNamesIndex = i + 1;
        return super.mo15c(arrayList.get(i));
    }

    @Override // shadersmod.transform.Namer
    /* renamed from: f */
    Names.Fiel mo16f(Names.Clas clas, String name, String desc) {
        ArrayList<String> arrayList = this.obfNames;
        int i = this.obfNamesIndex;
        this.obfNamesIndex = i + 1;
        return super.mo16f(clas, arrayList.get(i), desc);
    }

    @Override // shadersmod.transform.Namer
    /* renamed from: m */
    Names.Meth mo18m(Names.Clas clas, String name, String desc) {
        ArrayList<String> arrayList = this.obfNames;
        int i = this.obfNamesIndex;
        this.obfNamesIndex = i + 1;
        return super.mo18m(clas, arrayList.get(i), desc);
    }
}
