package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBakedQuadRetextured.class */
public class STBakedQuadRetextured implements IClassTransformer {
    private static final int vertexSize = 14;

    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 3);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBakedQuadRetextured$CVTransform.class */
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
            if (Names.bakedQuadRetextured_remapQuad.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patch method %s.%s%s", this.classname, name, desc);
                return new MVremapQuad(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBakedQuadRetextured$MVremapQuad.class */
    private static class MVremapQuad extends MethodVisitor {
        int count;

        public MVremapQuad(MethodVisitor mv) {
            super(262144, mv);
            this.count = 0;
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 16 && operand == 7) {
                this.mv.visitIntInsn(opcode, 14);
                this.count++;
                if (this.count > 1) {
                    SMCLog.warning("Unexpected number of 7.");
                    return;
                }
                return;
            }
            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, Names.bakedQuadRetextured_vertexData.clasName, Names.bakedQuadRetextured_vertexData.name, Names.bakedQuadRetextured_vertexData.desc);
                this.mv.visitMethodInsn(184, Names.sBakedQuad_onRemapQuad.clasName, Names.sBakedQuad_onRemapQuad.name, Names.sBakedQuad_onRemapQuad.desc);
            }
            super.visitInsn(opcode);
        }
    }
}
