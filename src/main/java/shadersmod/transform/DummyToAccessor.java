package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.InsnList;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/DummyToAccessor.class */
public class DummyToAccessor implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 0);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/DummyToAccessor$CVTransform.class */
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
            return new MVShadersModClient(this.cv.visitMethod(access, name, desc, signature, exceptions));
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/DummyToAccessor$MVShadersModClient.class */
    private static class MVShadersModClient extends MethodVisitor {
        public MVShadersModClient(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.dummy_.clasName.equals(owner)) {
                InsnList insnList = DummyData.mapDummyToInsnList.get(name);
                if (insnList != null) {
                    insnList.accept(this.mv);
                    return;
                } else {
                    SMCLog.error("Unknown dummy function %s %s", name, desc);
                    return;
                }
            }
            super.visitMethodInsn(opcode, owner, name, desc);
        }
    }
}
