package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBufferDrawer.class */
public class STVertexBufferDrawer implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBufferDrawer$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (Names.vertexBufferDrawer_draw.equalsNameDesc(name, desc)) {
                return new MVdraw(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBufferDrawer$MVdraw.class */
    private static class MVdraw extends MethodVisitor {
        public MVdraw(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.equals("org/lwjgl/opengl/GL11", "glDrawArrays", "(III)V", owner, name, desc)) {
                this.mv.visitVarInsn(25, 1);
                this.mv.visitMethodInsn(184, "shadersmod/client/SVertexBuilder", "drawArrays", "(III" + Names.vertexBuffer_.desc + ")V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }
}
