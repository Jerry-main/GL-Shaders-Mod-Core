package shadersmod.client;

import java.util.ArrayList;

/* loaded from: ShadersMod-v2.7.1mc1.12-dev.jar:shadersmod/client/GuiSlotShaders.class */
class GuiSlotShaders extends bjp {
    private ArrayList shaderslist;
    private int selectedIndex;
    final GuiShaders shadersGui;

    public GuiSlotShaders(GuiShaders par1GuiShaders) {
        super(par1GuiShaders.getMc(), (par1GuiShaders.l / 2) + 20, par1GuiShaders.m, 40, par1GuiShaders.m - 70, 16);
        this.shadersGui = par1GuiShaders;
        updateList();
    }

    public void updateList() {
        this.shaderslist = Shaders.listofShaders();
        this.selectedIndex = 0;
        int n = this.shaderslist.size();
        for (int i = 0; i < n; i++) {
            if (((String) this.shaderslist.get(i)).equals(Shaders.currentshadername)) {
                this.selectedIndex = i;
                return;
            }
        }
    }

    /* renamed from: b */
    protected int m7b() {
        return this.shaderslist.size();
    }

    /* renamed from: a */
    protected void m8a(int index, boolean doubleClicked, int mouseX, int mouseY) {
        this.selectedIndex = index;
        Shaders.setShaderPack((String) this.shaderslist.get(index));
        this.shadersGui.needReinit = false;
        Shaders.loadShaderPack();
        Shaders.uninit();
    }

    /* renamed from: a */
    protected boolean m9a(int index) {
        return index == this.selectedIndex;
    }

    /* renamed from: d */
    protected int m10d() {
        return this.b - 6;
    }

    /* renamed from: k */
    protected int m11k() {
        return m7b() * 18;
    }

    /* renamed from: a */
    protected void m12a() {
    }

    /* renamed from: a */
    protected void m13a(int index, int posX, int posY, int contentY, int mouseX, int mouseY, float p_192637_7_) {
        this.shadersGui.drawCenteredString((String) this.shaderslist.get(index), (this.shadersGui.l / 4) + 10, posY + 1, 16777215);
    }
}
