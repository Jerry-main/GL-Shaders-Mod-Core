package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STGuiOptions.class */
public class STGuiOptions implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STGuiOptions$CVTransform.class */
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
            if (Names.guiOptions_initGui.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patching method %s%s", name, desc);
                return new MVinitGui(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.guiOptions_actionPerformed.equalsNameDesc(name, desc)) {
                SMCLog.finer("  patching method %s%s", name, desc);
                return new MVactionPerformed(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STGuiOptions$MVinitGui.class */
    private static class MVinitGui extends MethodVisitor {
        int state;

        public MVinitGui(MethodVisitor mv) {
            super(262144, mv);
            this.state = 0;
        }

        public void visitInsn(int opcode) {
            super.visitInsn(opcode);
            if (opcode == 87 && this.state == 1) {
                this.state = 2;
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, Names.guiOptions_buttonList.clasName, Names.guiOptions_buttonList.name, Names.guiOptions_buttonList.desc);
                this.mv.visitTypeInsn(187, Names.guiButton_.clasName);
                this.mv.visitInsn(89);
                this.mv.visitIntInsn(17, 190);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, Names.guiOptions_width.clasName, Names.guiOptions_width.name, Names.guiOptions_width.desc);
                this.mv.visitInsn(5);
                this.mv.visitInsn(108);
                this.mv.visitIntInsn(17, 155);
                this.mv.visitInsn(100);
                this.mv.visitIntInsn(16, 76);
                this.mv.visitInsn(96);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, Names.guiOptions_height.clasName, Names.guiOptions_height.name, Names.guiOptions_height.desc);
                this.mv.visitIntInsn(16, 6);
                this.mv.visitInsn(108);
                this.mv.visitIntInsn(16, 96);
                this.mv.visitInsn(96);
                this.mv.visitIntInsn(16, 6);
                this.mv.visitInsn(100);
                this.mv.visitIntInsn(16, 74);
                this.mv.visitIntInsn(16, 20);
                this.mv.visitLdcInsn("Shaders...");
                this.mv.visitMethodInsn(183, Names.guiButton_.clasName, "<init>", "(IIIIILjava/lang/String;)V");
                this.mv.visitMethodInsn(185, "java/util/List", "add", "(Ljava/lang/Object;)Z");
                this.mv.visitInsn(87);
                SMCLog.finest("    add shaders button");
            }
        }

        public void visitLdcInsn(Object cst) {
            if ((cst instanceof String) && ((String) cst).equals("options.language") && this.state == 0) {
                this.state = 1;
                this.mv.visitInsn(87);
                this.mv.visitInsn(87);
                this.mv.visitIntInsn(16, 74);
                this.mv.visitIntInsn(16, 20);
                SMCLog.finest("    decrease language button size");
            }
            super.visitLdcInsn(cst);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STGuiOptions$MVactionPerformed.class */
    private static class MVactionPerformed extends MethodVisitor {
        public MVactionPerformed(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            super.visitCode();
            this.mv.visitVarInsn(25, 1);
            this.mv.visitFieldInsn(180, Names.guiButton_id.clasName, Names.guiButton_id.name, Names.guiButton_id.desc);
            this.mv.visitIntInsn(17, 190);
            Label l1 = new Label();
            this.mv.visitJumpInsn(160, l1);
            this.mv.visitVarInsn(25, 0);
            this.mv.visitFieldInsn(180, Names.guiOptions_mc.clasName, Names.guiOptions_mc.name, Names.guiOptions_mc.desc);
            this.mv.visitFieldInsn(180, Names.minecraft_gameSettings.clasName, Names.minecraft_gameSettings.name, Names.minecraft_gameSettings.desc);
            this.mv.visitMethodInsn(182, Names.gameSettings_saveOptions.clasName, Names.gameSettings_saveOptions.name, Names.gameSettings_saveOptions.desc);
            this.mv.visitVarInsn(25, 0);
            this.mv.visitFieldInsn(180, Names.guiOptions_mc.clasName, Names.guiOptions_mc.name, Names.guiOptions_mc.desc);
            this.mv.visitTypeInsn(187, "shadersmod/client/GuiShaders");
            this.mv.visitInsn(89);
            this.mv.visitVarInsn(25, 0);
            this.mv.visitVarInsn(25, 0);
            this.mv.visitFieldInsn(180, Names.guiOptions_options.clasName, Names.guiOptions_options.name, Names.guiOptions_options.desc);
            this.mv.visitMethodInsn(183, "shadersmod/client/GuiShaders", "<init>", "(" + Names.guiScreen_.desc + Names.gameSettings_.desc + ")V");
            this.mv.visitMethodInsn(182, Names.minecraft_displayGuiScreen.clasName, Names.minecraft_displayGuiScreen.name, Names.minecraft_displayGuiScreen.desc);
            this.mv.visitLabel(l1);
            this.mv.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
            SMCLog.finest("    shaders button action");
        }
    }
}
