package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBlockModelRenderer.class */
public class STBlockModelRenderer implements IClassTransformer {
    private static final int vertexSize = 14;

    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBlockModelRenderer$CVTransform.class */
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
            if (Names.blockModelRenderer_fillQuadBounds.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVfillQuadBounds(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBlockModelRenderer$MVfillQuadBounds.class */
    private static class MVfillQuadBounds extends MethodVisitor {
        int count;

        public MVfillQuadBounds(MethodVisitor mv) {
            super(262144, mv);
            this.count = 0;
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 16 && operand == 7) {
                this.mv.visitIntInsn(opcode, 14);
                this.count++;
                if (this.count > 3) {
                    SMCLog.warning("Unexpected number of 7.");
                    return;
                }
                return;
            }
            this.mv.visitIntInsn(opcode, operand);
        }
    }
}
