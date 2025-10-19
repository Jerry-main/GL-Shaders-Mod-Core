package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAtlasSprite.class */
public class STTextureAtlasSprite implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAtlasSprite$CVTransform.class */
    private static class CVTransform extends ClassVisitor {
        String classname;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            return this.cv.visitField((access & (-7)) | 1, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            int access2 = (access & (-7)) | 1;
            if (Names.textureAtlasSpri_updateAnimation.equalsNameDesc(name, desc)) {
                return new MVanimation(this.cv.visitMethod(access2, name, desc, signature, exceptions));
            }
            if (name.equals("load") && desc.equals("(" + Names.iResourceManager_.desc + Names.resourceLocation_.desc + ")Z")) {
                return new MVload(this.cv.visitMethod(access2, name, desc, signature, exceptions));
            }
            if (name.equals("uploadFrameTexture") && desc.equals("(III)V")) {
                return new MVuploadFrameTexture(this.cv.visitMethod(access2, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access2, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAtlasSprite$MVanimation.class */
    protected static class MVanimation extends MethodVisitor {
        public MVanimation(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.textureUtil_uploadTexSub.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "uploadTexSub", "([[IIIIIZZ)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAtlasSprite$MVload.class */
    private static class MVload extends MethodVisitor {
        public MVload(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.iResourceManager_getResource.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "loadResource", "(" + Names.iResourceManager_.desc + Names.resourceLocation_.desc + ")" + Names.iResource_.desc);
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAtlasSprite$MVloadSprite.class */
    private static class MVloadSprite extends MethodVisitor {
        public MVloadSprite(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 188 && operand == 10) {
                this.mv.visitInsn(6);
                this.mv.visitInsn(104);
            }
            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.equals("java/awt/image/BufferedImage", "getRGB", "(IIII[III)[I", owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "loadAtlasSprite", "(Ljava/awt/image/BufferedImage;IIII[III)[I");
                return;
            }
            if (Names.textureAtlasSpri_getFrameTextureData.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "getFrameTexData", "([[IIII)[[I");
            } else if (Names.equals(Names.textureAtlasSpri_.clasName, "fixTransparentColor", "([I)V", owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "fixTransparentColor", "(" + Names.textureAtlasSpri_.desc + "[I)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureAtlasSprite$MVuploadFrameTexture.class */
    private static class MVuploadFrameTexture extends MethodVisitor {
        public MVuploadFrameTexture(MethodVisitor mv) {
            super(262144, (MethodVisitor) null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(25, 0);
            mv.visitVarInsn(21, 1);
            mv.visitVarInsn(21, 2);
            mv.visitVarInsn(21, 3);
            mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "uploadFrameTexture", "(" + Names.textureAtlasSpri_.desc + "III)V");
            mv.visitInsn(177);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", Names.textureAtlasSpri_.desc, (String) null, l0, l2, 0);
            mv.visitLocalVariable("frameIndex", "I", (String) null, l0, l2, 1);
            mv.visitLocalVariable("xPos", "I", (String) null, l0, l2, 2);
            mv.visitLocalVariable("yPos", "I", (String) null, l0, l2, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
        }
    }
}
