package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STChunkRenderDispatcher.class */
public class STChunkRenderDispatcher implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 0);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STChunkRenderDispatcher$CVTransform.class */
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

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (Names.chunkRenderDispatcher_init.equalsNameDesc(name, desc)) {
                return new MVinit(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STChunkRenderDispatcher$MVinit.class */
    private static class MVinit extends MethodVisitor {
        public MVinit(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitTypeInsn(int opcode, String type) {
            if (opcode == 187 && Names.vertexBufferDrawer_.name.equals(type)) {
                this.mv.visitTypeInsn(opcode, Names.sVertexBufferDrawerChunk_.name);
            } else {
                this.mv.visitTypeInsn(opcode, type);
            }
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.vertexBufferDrawer_init.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(opcode, Names.sVertexBufferDrawerChunk_init.clasName, Names.sVertexBufferDrawerChunk_init.name, Names.sVertexBufferDrawerChunk_init.desc);
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }
}
