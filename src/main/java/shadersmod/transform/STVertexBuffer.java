package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBuffer.class */
public class STVertexBuffer implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBuffer$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        boolean endFields;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
            this.endFields = false;
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (Names.vertexBuffer_sVertexBuffer.name.equals(name)) {
                return null;
            }
            return this.cv.visitField((access & (-7)) | 1, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.endFields) {
                this.endFields = true;
                FieldVisitor fv = this.cv.visitField(1, Names.vertexBuffer_sVertexBuffer.name, Names.vertexBuffer_sVertexBuffer.desc, (String) null, (Object) null);
                fv.visitEnd();
            }
            if (Names.equalsNameDesc("<init>", "(I)V", name, desc)) {
                return new MVinit(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.vertexBuffer_addVertexData.equalsNameDesc(name, desc)) {
                return new MVaddData(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.vertexBuffer_endVertex.equalsNameDesc(name, desc)) {
                return new MVendVertex(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.vertexBuffer_begin.equalsNameDesc(name, desc)) {
                return new MVbegin(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.vertexBuffer_reset.equalsNameDesc(name, desc)) {
                return new MVreset(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            AccessorGenerator.generateFieldAccessor(this.cv, "rawIntBuffer", Names.vertexBuffer_rawIntBuffer);
            AccessorGenerator.generateFieldAccessor(this.cv, "rawFloatBuffer", Names.vertexBuffer_rawFloatBuffer);
            AccessorGenerator.generateFieldAccessor(this.cv, "vertexCount", Names.vertexBuffer_vertexCount);
            AccessorGenerator.generateFieldAccessor(this.cv, "drawMode", Names.vertexBuffer_drawMode);
            AccessorGenerator.generateFieldAccessor(this.cv, "vertexElement", Names.vertexBuffer_vertexElement);
            AccessorGenerator.generateFieldAccessor(this.cv, "vertexElementIndex", Names.vertexBuffer_vertexElementIndex);
            super.visitEnd();
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBuffer$MVinit.class */
    private static class MVinit extends MethodVisitor {
        public MVinit(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "onVertexBufferInit", "(" + Names.vertexBuffer_.desc + ")V");
            }
            this.mv.visitInsn(opcode);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBuffer$MVaddData.class */
    private static class MVaddData extends MethodVisitor {
        public MVaddData(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(25, 0);
            this.mv.visitVarInsn(25, 1);
            this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "beginAddVertexData", "(" + Names.vertexBuffer_.desc + "[I)V");
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "endAddVertexData", "(" + Names.vertexBuffer_.desc + ")V");
            }
            this.mv.visitInsn(opcode);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBuffer$MVendVertex.class */
    private static class MVendVertex extends MethodVisitor {
        public MVendVertex(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "endEndVertex", "(" + Names.vertexBuffer_.desc + ")V");
            }
            this.mv.visitInsn(opcode);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBuffer$MVbegin.class */
    private static class MVbegin extends MethodVisitor {
        public MVbegin(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (opcode == 181 && Names.vertexBuffer_vertexFormat.equals(owner, name, desc)) {
                this.mv.visitFieldInsn(opcode, owner, name, desc);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "onVertexBufferBegin", "(" + Names.vertexBuffer_.desc + ")V");
                return;
            }
            this.mv.visitFieldInsn(opcode, owner, name, desc);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STVertexBuffer$MVreset.class */
    private static class MVreset extends MethodVisitor {
        public MVreset(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (opcode == 181 && Names.vertexBuffer_vertexFormat.equals(owner, name, desc)) {
                this.mv.visitFieldInsn(opcode, owner, name, desc);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "onVertexBufferBegin", "(" + Names.vertexBuffer_.desc + ")V");
                return;
            }
            this.mv.visitFieldInsn(opcode, owner, name, desc);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, Names.sVertexBuffer_.clasName, "onVertexBufferReset", "(" + Names.vertexBuffer_.desc + ")V");
            }
            super.visitInsn(opcode);
        }
    }
}
