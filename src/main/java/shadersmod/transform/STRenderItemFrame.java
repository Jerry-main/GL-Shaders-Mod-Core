package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderItemFrame.class */
public class STRenderItemFrame implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderItemFrame$CVTransform.class */
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
            if (Names.renderItemFrame_renderItemInFrame.equalsNameDesc(name, desc)) {
                return new MVrenderItem(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderItemFrame$MVrenderItem.class */
    private static class MVrenderItem extends MethodVisitor {
        int state;

        public MVrenderItem(MethodVisitor mv) {
            super(262144, mv);
            this.state = 0;
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.textureManager_bindTexture.equals(owner, name, desc)) {
                if (this.state == 0) {
                    this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "bindTextureMapForUpdateAndRender", "(" + Names.textureManager_.desc + Names.resourceLocation_.desc + ")V");
                    return;
                }
                this.state++;
            }
            this.mv.visitMethodInsn(opcode, owner, name, desc);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitInsn(1);
                this.mv.visitFieldInsn(179, "shadersmod/client/ShadersTex", "updatingTex", "Lshadersmod/client/MultiTexID;");
            }
            this.mv.visitInsn(opcode);
        }
    }
}
