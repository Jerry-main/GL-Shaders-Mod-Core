package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STDefVertexFormat.class */
public class STDefVertexFormat implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 0);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STDefVertexFormat$CVTransform.class */
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
            if (Names.equalsNameDesc("<clinit>", "()V", name, desc)) {
                return new MVclinit(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STDefVertexFormat$MVclinit.class */
    private static class MVclinit extends MethodVisitor {
        public MVclinit(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitTypeInsn(int opcode, String type) {
            if (opcode == 187 && Names.vertexFormatElement_.name.equals(type)) {
                this.mv.visitTypeInsn(opcode, Names.sVertexElement_.name);
            } else {
                this.mv.visitTypeInsn(opcode, type);
            }
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.vertexFormatElement_init.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(opcode, Names.sVertexElement_init.clasName, Names.sVertexElement_init.name, Names.sVertexElement_init.desc);
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitMethodInsn(184, Names.sVertexFormat_onDefaultVertexFormatsClinit.clasName, Names.sVertexFormat_onDefaultVertexFormatsClinit.name, Names.sVertexFormat_onDefaultVertexFormatsClinit.desc);
            }
            super.visitInsn(opcode);
        }
    }
}
