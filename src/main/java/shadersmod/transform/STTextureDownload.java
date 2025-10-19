package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureDownload.class */
public class STTextureDownload implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureDownload$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            return super.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getMultiTexID")) {
                return null;
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            MethodVisitor mv = this.cv.visitMethod(1, "getMultiTexID", "()Lshadersmod/client/MultiTexID;", (String) null, (String[]) null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, Names.textureDownload_textureUploaded.clasName, Names.textureDownload_textureUploaded.name, Names.textureDownload_textureUploaded.desc);
            Label l1 = new Label();
            mv.visitJumpInsn(154, l1);
            mv.visitVarInsn(25, 0);
            mv.visitMethodInsn(182, Names.textureDownload_.clasName, Names.iTextureObject_getGlTextureId.name, Names.iTextureObject_getGlTextureId.desc);
            mv.visitInsn(87);
            mv.visitLabel(l1);
            mv.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
            mv.visitVarInsn(25, 0);
            mv.visitMethodInsn(183, Names.abstractTexture_.clasName, "getMultiTexID", "()Lshadersmod/client/MultiTexID;");
            mv.visitInsn(176);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", Names.textureDownload_.desc, (String) null, l0, l3, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            this.cv.visitEnd();
        }
    }
}
