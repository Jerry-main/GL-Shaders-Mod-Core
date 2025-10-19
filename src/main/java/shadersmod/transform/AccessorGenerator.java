package shadersmod.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import shadersmod.transform.Names;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/transform/AccessorGenerator.class */
public class AccessorGenerator {
    private static int getVarLoadOpcode(String desc) {
        switch (desc.charAt(0)) {
        }
        return 21;
    }

    private static int getReturnOpcode(String desc) {
        switch (desc.charAt(0)) {
        }
        return 172;
    }

    public static void generateFieldAccessor(ClassVisitor cv, String accessorName, Names.Fiel fiel) {
        MethodVisitor mv = cv.visitMethod(9, "put_" + accessorName, "(L" + fiel.clasName + ";" + fiel.desc + ")V", (String) null, (String[]) null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(getVarLoadOpcode(fiel.desc), 1);
        mv.visitFieldInsn(181, fiel.clasName, fiel.name, fiel.desc);
        mv.visitInsn(177);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        MethodVisitor mv2 = cv.visitMethod(9, "get_" + accessorName, "(L" + fiel.clasName + ";)" + fiel.desc, (String) null, (String[]) null);
        mv2.visitCode();
        mv2.visitVarInsn(25, 0);
        mv2.visitFieldInsn(180, fiel.clasName, fiel.name, fiel.desc);
        mv2.visitInsn(getReturnOpcode(fiel.desc));
        mv2.visitMaxs(1, 1);
        mv2.visitEnd();
    }

    public static void generatePrivateMethodAccessor(ClassVisitor cv, String accessorName, Names.Meth meth) {
        String methDesc = meth.desc;
        int indexCloseParenthesis = methDesc.indexOf(41);
        String returnType = methDesc.substring(indexCloseParenthesis + 1);
        String allParams = methDesc.substring(1, indexCloseParenthesis);
        MethodVisitor mv = cv.visitMethod(9, "invoke_" + accessorName, "(L" + meth.clasName + ";" + allParams + ")" + returnType, (String) null, (String[]) null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        int paramCount = 1;
        int i = 1;
        while (methDesc.charAt(i) != ')') {
            int startIndex = i;
            while (methDesc.charAt(i) == '[') {
                i++;
            }
            if (methDesc.charAt(i) == 'L') {
                do {
                    i++;
                } while (methDesc.charAt(i) != ';');
            }
            i++;
            String desc = methDesc.substring(startIndex, i);
            mv.visitVarInsn(getVarLoadOpcode(desc), paramCount);
            char c = desc.charAt(0);
            paramCount += (c == 'D' || c == 'J') ? 2 : 1;
        }
        mv.visitMethodInsn(183, meth.clasName, meth.name, meth.desc);
        mv.visitInsn(getReturnOpcode(returnType));
        mv.visitMaxs(paramCount, paramCount);
        mv.visitEnd();
    }
}
