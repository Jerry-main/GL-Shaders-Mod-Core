package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureSimple.class */
public class STTextureSimple implements IClassTransformer {
    private static final int logDetail = 0;

    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 3);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureSimple$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (Names.iTextureObject_loadTexture.equalsNameDesc(name, desc)) {
                return new MVloadTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureSimple$MVloadTexture.class */
    private static class MVloadTexture extends MethodVisitor {
        public MVloadTexture(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.textureUtil_uploadTextureImageAllocate.equals(owner, name, desc)) {
                this.mv.visitVarInsn(25, 1);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, Names.simpleTexture_textureLocation.clasName, Names.simpleTexture_textureLocation.name, Names.simpleTexture_textureLocation.desc);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(182, Names.simpleTexture_.clasName, "getMultiTexID", "()Lshadersmod/client/MultiTexID;");
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "loadSimpleTexture", "(ILjava/awt/image/BufferedImage;ZZ" + Names.iResourceManager_.desc + Names.resourceLocation_.desc + "Lshadersmod/client/MultiTexID;)I");
                SMCLog.finer("    loadSimpleTexture");
                return;
            }
            this.mv.visitMethodInsn(opcode, owner, name, desc);
        }
    }
}
