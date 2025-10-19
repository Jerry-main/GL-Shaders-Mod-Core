package shadersmod.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import shadersmod.common.SMCLog;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderManager.class */
public class STRenderManager implements IClassTransformer {
    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", par1, par2);
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 0);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/STRenderManager$CVTransform.class */
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
            if (name.equals("getFieldEntityRenderMap") && desc.equals("()Ljava/util/Map;")) {
                return null;
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            AccessorGenerator.generateFieldAccessor(this.cv, "entityRenderMap", Names.renderManager_entityRenderMap);
        }
    }
}
