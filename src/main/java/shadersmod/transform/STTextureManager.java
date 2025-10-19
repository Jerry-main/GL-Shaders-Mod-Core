package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureManager.class */
public class STTextureManager implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureManager$CVTransform.class */
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
            if (Names.textureManager_bindTexture.equalsNameDesc(name, desc)) {
                return new MVbindTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            if (Names.textureManager_onResourceManagerReload.equalsNameDesc(name, desc)) {
                return new MVonReload(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureManager$MVbindTexture.class */
    private static class MVbindTexture extends MethodVisitor {
        public MVbindTexture(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.iTextureObject_getGlTextureId.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "bindTexture", "(" + Names.iTextureObject_.desc + ")V");
            } else {
                if (Names.textureUtil_bindTexture.equals(owner, name, desc)) {
                    return;
                }
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STTextureManager$MVonReload.class */
    private static class MVonReload extends MethodVisitor {
        public MVonReload(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (Names.iTextureObject_getGlTextureId.equals(owner, name, desc)) {
                this.mv.visitMethodInsn(184, "shadersmod/client/ShadersTex", "deleteMultiTex", "(" + Names.iTextureObject_.desc + ")I");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }
}
