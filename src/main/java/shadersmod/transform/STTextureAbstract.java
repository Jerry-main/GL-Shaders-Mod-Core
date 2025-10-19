package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAbstract.class */
public class STTextureAbstract implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAbstract$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;
        boolean endFields;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
            this.endFields = false;
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (name.equals("multiTex")) {
                return null;
            }
            return this.cv.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.endFields) {
                this.endFields = true;
                FieldVisitor fv = this.cv.visitField(1, "multiTex", "Lshadersmod/client/MultiTexID;", (String) null, (Object) null);
                fv.visitEnd();
            }
            if (Names.abstractTexture_deleteGlTexture.equalsNameDesc(name, desc)) {
                return new MVdeleteGlTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (name.equals("getMultiTexID") && desc.equals("()Lshadersmod/client/MultiTexID;")) {
                return null;
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            MethodVisitor mv = this.cv.visitMethod(1, "getMultiTexID", "()Lshadersmod/client/MultiTexID;", (String) null, (String[]) null);
            mv.visitCode();
            mv.visitVarInsn(25, 0);
            mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "getMultiTexID", "(" + Names.abstractTexture_.desc + ")Lshadersmod/client/MultiTexID;");
            mv.visitInsn(176);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            AccessorGenerator.generateFieldAccessor(this.cv, "glTextureId", Names.abstractTexture_glTextureId);
            this.cv.visitEnd();
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAbstract$MVdeleteGlTexture.class */
    private static class MVdeleteGlTexture extends MethodVisitor {
        public MVdeleteGlTexture(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(25, 0);
            this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "deleteTextures", "(" + Names.abstractTexture_.desc + ")V");
        }
    }
}
