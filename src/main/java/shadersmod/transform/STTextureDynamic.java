package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureDynamic.class */
public class STTextureDynamic implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureDynamic$CVTransform.class */
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
            if (name.equals("<init>") && desc.equals("(II)V")) {
                return new MVinit(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.dynamicTexture_updateDynamicTexture.equalsNameDesc(name, desc)) {
                return new MVupdate(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureDynamic$MVinit.class */
    private static class MVinit extends MethodVisitor {
        public MVinit(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 188 && operand == 10) {
                this.mv.visitInsn(6);
                this.mv.visitInsn(104);
            }
            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.textureUtil_allocateTexture.equals(owner, name, desc)) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "initDynamicTexture", "(III" + Names.dynamicTexture_.desc + ")V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureDynamic$MVupdate.class */
    private static class MVupdate extends MethodVisitor {
        public MVupdate(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.textureUtil_uploadTexture.equals(owner, name, desc)) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "updateDynamicTexture", "(I[III" + Names.dynamicTexture_.desc + ")V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }
}
