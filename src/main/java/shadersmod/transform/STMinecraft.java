package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STMinecraft.class */
public class STMinecraft implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STMinecraft$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            super.visit(version, access, name, signature, superName, interfaces);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (Names.minecraft_startGame.equalsNameDesc(name, desc)) {
                return new MVstartGame(super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STMinecraft$MVstartGame.class */
    private static class MVstartGame extends MethodVisitor {
        int state;

        public MVstartGame(MethodVisitor mv) {
            super(262144, mv);
            this.state = 0;
        }

        public void visitLdcInsn(Object cst) {
            if ((cst instanceof String) && ((String) cst).equals("Startup") && this.state == 0) {
                this.state = 1;
            }
            super.visitLdcInsn(cst);
        }

        public void visitVarInsn(int opcode, int var) {
            if (opcode == 25 && var == 0 && this.state == 1) {
                this.state = 2;
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, "shadersmod/client/Shaders", "startup", "(" + Names.minecraft_.desc + ")V");
            }
            super.visitVarInsn(opcode, var);
        }
    }
}
