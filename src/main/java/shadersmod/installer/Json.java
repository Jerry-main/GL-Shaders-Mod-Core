package shadersmod.installer;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Json.class */
public class Json {

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Json$JsValue.class */
    public static class JsValue {
        void print(Writer wr, int indent) throws IOException {
        }

        void println(Writer wr, int indent) throws IOException {
            wr.append("\n");
            for (int i = 0; i < indent; i++) {
                wr.append("  ");
            }
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Json$JsString.class */
    public static class JsString extends JsValue {
        String str;

        public JsString() {
        }

        public JsString(String str) {
            this.str = str;
        }

        @Override // shadersmod.installer.Json.JsValue
        void print(Writer wr, int indent) throws IOException {
            wr.append("\"").append((CharSequence) this.str).append("\"");
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Json$JsNumber.class */
    public static class JsNumber extends JsValue {
        String str;

        @Override // shadersmod.installer.Json.JsValue
        void print(Writer wr, int indent) throws IOException {
            wr.append((CharSequence) this.str);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Json$JsBoolean.class */
    public static class JsBoolean extends JsValue {
        String str;

        public JsBoolean() {
        }

        public JsBoolean(String str) {
            this.str = str;
        }

        @Override // shadersmod.installer.Json.JsValue
        void print(Writer wr, int indent) throws IOException {
            wr.append((CharSequence) this.str);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Json$JsArray.class */
    public static class JsArray extends JsValue {
        ArrayList<JsValue> elements = new ArrayList<>();

        @Override // shadersmod.installer.Json.JsValue
        void print(Writer wr, int indent) throws IOException {
            wr.append("[");
            int indent1 = indent + 1;
            int n = this.elements.size();
            for (int i = 0; i < n; i++) {
                JsValue e = this.elements.get(i);
                println(wr, indent1);
                e.print(wr, indent1);
                if (i + 1 < n) {
                    wr.append(",");
                }
            }
            println(wr, indent);
            wr.append("]");
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Json$JsField.class */
    public static class JsField extends JsValue {
        String name;
        JsValue value;

        @Override // shadersmod.installer.Json.JsValue
        void print(Writer wr, int indent) throws IOException {
            wr.append("\"").append((CharSequence) this.name).append("\": ");
            this.value.print(wr, indent);
        }
    }

    /* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/Json$JsObject.class */
    public static class JsObject extends JsValue {
        ArrayList<JsField> members = new ArrayList<>();
        Map<String, JsField> membersMap = new HashMap();

        @Override // shadersmod.installer.Json.JsValue
        void print(Writer wr, int indent) throws IOException {
            wr.append("{");
            int indent1 = indent + 1;
            int n = this.members.size();
            for (int i = 0; i < n; i++) {
                JsField m = this.members.get(i);
                println(wr, indent1);
                m.print(wr, indent1);
                if (i + 1 < n) {
                    wr.append(",");
                }
            }
            println(wr, indent);
            wr.append("}");
        }

        void put(String name, JsValue value) {
            JsField jsf = this.membersMap.get(name);
            if (jsf == null) {
                JsField jsf2 = new JsField();
                jsf2.name = name;
                jsf2.value = value;
                this.members.add(jsf2);
                this.membersMap.put(name, jsf2);
                return;
            }
            jsf.value = value;
        }

        JsValue get(String name) {
            JsField jsf = this.membersMap.get(name);
            if (jsf == null) {
                return null;
            }
            return jsf.value;
        }
    }
}
