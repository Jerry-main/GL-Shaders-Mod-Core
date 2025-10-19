package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBlockRendererDispatcher.class */
public class STBlockRendererDispatcher implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBlockRendererDispatcher$CVTransform.class */
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
            if (Names.blockRendererDispatcher_renderBlock.equalsNameDesc(name, desc)) {
                return new MVrenderBlock(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STBlockRendererDispatcher$MVrenderBlock.class */
    private static class MVrenderBlock extends MethodVisitor {
        public MVrenderBlock(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.blockFluidRenderer_renderFluid.equals(owner, name, desc)) {
                this.mv.visitVarInsn(25, 1);
                this.mv.visitVarInsn(25, 2);
                this.mv.visitVarInsn(25, 3);
                this.mv.visitVarInsn(25, 4);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "pushEntity", "(" + Names.iBlockState_.desc + Names.blockPos_.desc + Names.iBlockAccess_.desc + Names.vertexBuffer_.desc + ")V");
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitVarInsn(25, 4);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "popEntity", "(" + Names.vertexBuffer_.desc + ")V");
                return;
            }
            if (Names.blockModelRenderer_renderModel.equals(owner, name, desc)) {
                this.mv.visitVarInsn(25, 1);
                this.mv.visitVarInsn(25, 2);
                this.mv.visitVarInsn(25, 3);
                this.mv.visitVarInsn(25, 4);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "pushEntity", "(" + Names.iBlockState_.desc + Names.blockPos_.desc + Names.iBlockAccess_.desc + Names.vertexBuffer_.desc + ")V");
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitVarInsn(25, 4);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "popEntity", "(" + Names.vertexBuffer_.desc + ")V");
                return;
            }
            this.mv.visitMethodInsn(opcode, owner, name, desc);
        }
    }
}
