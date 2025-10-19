package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexFormat.class */
public class STVertexFormat implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 0);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexFormat$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;
        boolean endFields;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
            this.endFields = false;
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            super.visit(version, access, name, signature, superName, interfaces);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.endFields) {
                this.endFields = true;
                FieldVisitor fv = this.cv.visitField(1, Names.vertexFormat_tangentElementOffset.name, Names.vertexFormat_tangentElementOffset.desc, (String) null, (Object) null);
                fv.visitEnd();
                FieldVisitor fv2 = this.cv.visitField(1, Names.vertexFormat_entityElementOffset.name, Names.vertexFormat_entityElementOffset.desc, (String) null, (Object) null);
                fv2.visitEnd();
                FieldVisitor fv3 = this.cv.visitField(1, Names.vertexFormat_miduvElementOffset.name, Names.vertexFormat_miduvElementOffset.desc, (String) null, (Object) null);
                fv3.visitEnd();
            }
            if (Names.vertexFormat_addElement.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patching method %s.%s%s", this.classname, name, desc);
                return new MVaddElement(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            AccessorGenerator.generateFieldAccessor(this.cv, "offsets", Names.vertexFormat_offsets);
            AccessorGenerator.generateFieldAccessor(this.cv, "nextOffset", Names.vertexFormat_nextOffset);
            AccessorGenerator.generateFieldAccessor(this.cv, "colorElementOffset", Names.vertexFormat_colorElementOffset);
            AccessorGenerator.generateFieldAccessor(this.cv, "uvOffsets", Names.vertexFormat_uvOffsets);
            AccessorGenerator.generateFieldAccessor(this.cv, "normalElementOffset", Names.vertexFormat_normalElementOffset);
            super.visitEnd();
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexFormat$MVaddElement.class */
    private static class MVaddElement extends MethodVisitor {
        public MVaddElement(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(25, 0);
            this.mv.visitVarInsn(25, 1);
            this.mv.visitMethodInsn(184, Names.sVertexFormat_onAddElement.clasName, Names.sVertexFormat_onAddElement.name, Names.sVertexFormat_onAddElement.desc);
        }
    }
}
