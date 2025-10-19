package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STModelRenderer.class */
public class STModelRenderer implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STModelRenderer$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;
        boolean fieldsAdded;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
            this.fieldsAdded = false;
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if ("savedScale".equals(name)) {
                return null;
            }
            return this.cv.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.fieldsAdded) {
                this.fieldsAdded = true;
                FieldVisitor fv = this.cv.visitField(0, "savedScale", "F", (String) null, (Object) null);
                fv.visitEnd();
            }
            if (Names.modelRenderer_compileDisplayList.equalsNameDesc(name, desc)) {
                return new MVcompile(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (!"resetDisplayList".equals(name) || !"()V".equals(desc)) {
                if (!"getCompiled".equals(name) || !"()Z".equals(desc)) {
                    if ("getDisplayList".equals(name) && "()I".equals(desc)) {
                        return null;
                    }
                    return this.cv.visitMethod(access, name, desc, signature, exceptions);
                }
                return null;
            }
            return null;
        }

        public void visitEnd() {
            MethodVisitor mv = this.cv.visitMethod(1, "getCompiled", "()Z", (String) null, (String[]) null);
            mv.visitCode();
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, Names.modelRenderer_compiled.clasName, Names.modelRenderer_compiled.name, Names.modelRenderer_compiled.desc);
            mv.visitInsn(172);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            MethodVisitor mv2 = this.cv.visitMethod(1, "getDisplayList", "()I", (String) null, (String[]) null);
            mv2.visitCode();
            mv2.visitVarInsn(25, 0);
            mv2.visitFieldInsn(180, Names.modelRenderer_displayList.clasName, Names.modelRenderer_displayList.name, Names.modelRenderer_displayList.desc);
            mv2.visitInsn(172);
            mv2.visitMaxs(1, 1);
            mv2.visitEnd();
            MethodVisitor mv3 = this.cv.visitMethod(1, "resetDisplayList", "()V", (String) null, (String[]) null);
            mv3.visitCode();
            mv3.visitVarInsn(25, 0);
            mv3.visitFieldInsn(180, Names.modelRenderer_compiled.clasName, Names.modelRenderer_compiled.name, Names.modelRenderer_compiled.desc);
            Label l1 = new Label();
            mv3.visitJumpInsn(153, l1);
            mv3.visitVarInsn(25, 0);
            mv3.visitInsn(3);
            mv3.visitFieldInsn(181, Names.modelRenderer_compiled.clasName, Names.modelRenderer_compiled.name, Names.modelRenderer_compiled.desc);
            mv3.visitVarInsn(25, 0);
            mv3.visitVarInsn(25, 0);
            mv3.visitFieldInsn(180, Names.modelRenderer_.clasName, "savedScale", "F");
            mv3.visitMethodInsn(183, Names.modelRenderer_compileDisplayList.clasName, Names.modelRenderer_compileDisplayList.name, Names.modelRenderer_compileDisplayList.desc);
            mv3.visitLabel(l1);
            mv3.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
            mv3.visitInsn(177);
            mv3.visitMaxs(2, 1);
            mv3.visitEnd();
            super.visitEnd();
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STModelRenderer$MVcompile.class */
    private static class MVcompile extends MethodVisitor {
        int state;
        Label label1;

        public MVcompile(MethodVisitor mv) {
            super(262144, mv);
            this.state = 0;
        }

        public void visitVarInsn(int opcode, int var) {
            if (this.state == 0 && opcode == 25 && var == 0) {
                this.state++;
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, Names.modelRenderer_displayList.clasName, Names.modelRenderer_displayList.name, Names.modelRenderer_displayList.desc);
                this.label1 = new Label();
                this.mv.visitJumpInsn(154, this.label1);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitVarInsn(23, 1);
                this.mv.visitFieldInsn(181, Names.modelRenderer_.clasName, "savedScale", "F");
                this.mv.visitVarInsn(25, 0);
                return;
            }
            super.visitVarInsn(opcode, var);
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (this.state == 1 && Names.modelRenderer_displayList.equals(owner, name, desc) && opcode == 181) {
                this.state++;
                this.mv.visitFieldInsn(opcode, owner, name, desc);
                this.mv.visitLabel(this.label1);
                this.label1 = null;
                this.mv.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
                return;
            }
            this.mv.visitFieldInsn(opcode, owner, name, desc);
        }
    }
}
