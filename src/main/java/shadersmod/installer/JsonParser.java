package shadersmod.installer;

import java.io.BufferedReader;
import java.io.IOException;
import shadersmod.installer.Json;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/installer/JsonParser.class */
public class JsonParser {

    /* renamed from: rd */
    protected BufferedReader f3rd;
    String data;

    public JsonParser(BufferedReader rd) {
        this.f3rd = rd;
    }

    int peekChar() throws IOException {
        do {
            if (this.data != null && this.data.length() != 0) {
                break;
            }
            this.data = this.f3rd.readLine();
        } while (this.data != null);
        if (this.data != null) {
            return this.data.charAt(0);
        }
        return 0;
    }

    void skip(int n) {
        if (this.data != null) {
            this.data = this.data.substring(n);
        }
    }

    void skipWS() throws IOException {
        while (peekChar() <= 32 && this.data != null) {
            skip(1);
        }
    }

    Json.JsValue getValue() throws IOException {
        skipWS();
        int c = peekChar();
        if (c == 34) {
            return getString();
        }
        if (c == 91) {
            return getArray();
        }
        if (c == 123) {
            return getObject();
        }
        if ((c >= 48 && c <= 57) || c == 45) {
            return getNumber();
        }
        if (c == 116 || c == 102) {
            return getBoolean();
        }
        return null;
    }

    Json.JsString getString() {
        char c;
        int pos = 1;
        int max = this.data.length();
        while (pos < max && (c = this.data.charAt(pos)) != '\"') {
            if (c == '\\') {
                pos += 2;
            } else {
                pos++;
            }
        }
        if (pos < max) {
            Json.JsString jss = new Json.JsString();
            jss.str = this.data.substring(1, pos);
            skip(pos + 1);
            return jss;
        }
        return null;
    }

    Json.JsNumber getNumber() {
        char c;
        int pos = 1;
        int max = this.data.length();
        while (pos < max && ((c = this.data.charAt(pos)) == '-' || c == '+' || c == '.' || ((c >= '0' && c <= '9') || c == 'e' || c == 'E'))) {
            pos++;
        }
        if (pos <= max) {
            Json.JsNumber jsn = new Json.JsNumber();
            jsn.str = this.data.substring(0, pos);
            skip(pos);
            return jsn;
        }
        return null;
    }

    Json.JsBoolean getBoolean() {
        if (this.data.startsWith("true")) {
            Json.JsBoolean jsb = new Json.JsBoolean();
            jsb.str = "true";
            skip(4);
            return jsb;
        }
        if (this.data.startsWith("false")) {
            Json.JsBoolean jsb2 = new Json.JsBoolean();
            jsb2.str = "false";
            skip(5);
            return jsb2;
        }
        return null;
    }

    Json.JsArray getArray() throws IOException {
        Json.JsValue jsv;
        char c;
        Json.JsArray jsa = new Json.JsArray();
        do {
            skip(1);
            skipWS();
            char c2 = this.data.charAt(0);
            if (c2 == ']' || (jsv = getValue()) == null) {
                break;
            }
            jsa.elements.add(jsv);
            skipWS();
            c = this.data.charAt(0);
        } while (c == ',');
        skip(1);
        return jsa;
    }

    Json.JsField getField() throws IOException {
        Json.JsField jsf = new Json.JsField();
        Json.JsString name = getString();
        skipWS();
        skip(1);
        Json.JsValue value = getValue();
        jsf.name = name.str;
        jsf.value = value;
        return jsf;
    }

    Json.JsObject getObject() throws IOException {
        Json.JsField jsf;
        char c;
        Json.JsObject jso = new Json.JsObject();
        do {
            skipWS();
            skip(1);
            skipWS();
            char c2 = this.data.charAt(0);
            if (c2 == '}' || (jsf = getField()) == null) {
                break;
            }
            jso.members.add(jsf);
            jso.membersMap.put(jsf.name, jsf);
            skipWS();
            c = this.data.charAt(0);
        } while (c == ',');
        skip(1);
        return jso;
    }
}
