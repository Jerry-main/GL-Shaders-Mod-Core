package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRegionRenderCacheBuilder.class */
public class STRegionRenderCacheBuilder implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 0);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRegionRenderCacheBuilder$CVTransform.class */
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
            if (Names.regionRenderCacheBuilder_init.equalsNameDesc(name, desc)) {
                return new MVinit(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRegionRenderCacheBuilder$MVinit.class */
    private static class MVinit extends MethodVisitor {
        public MVinit(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitLdcInsn(Object cst) {
            int value;
            if ((cst instanceof Integer) && ((value = ((Integer) cst).intValue()) == 2097152 || value == 131072 || value == 262144)) {
                super.visitLdcInsn(new Integer((value * 14) / 7));
            } else {
                super.visitLdcInsn(cst);
            }
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, Names.regionRenderCacheBuilder_vertexBuffers.clasName, Names.regionRenderCacheBuilder_vertexBuffers.name, Names.regionRenderCacheBuilder_vertexBuffers.desc);
                this.mv.visitFieldInsn(178, Names.blockRenderLayer_translucent.clasName, Names.blockRenderLayer_translucent.name, Names.blockRenderLayer_translucent.desc);
                this.mv.visitMethodInsn(182, Names.blockRenderLayer_.clasName, "ordinal", "()I", false);
                this.mv.visitInsn(50);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_enableCalcNormal.clasName, Names.sVertexBuffer_enableCalcNormal.name, Names.sVertexBuffer_enableCalcNormal.desc, false);
            }
            super.visitInsn(opcode);
        }
    }
}
