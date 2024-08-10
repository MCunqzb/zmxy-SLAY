package patchs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import monsters.act2.WheelTurningKing;
import monsters.act3.YangJian;


@SpirePatch(clz = TheBeyondScene.class, method = "renderCombatRoomBg")
public class TheBeyondSceneBGPatch {
    private static String path1 = "img/scene/zmxy3-4.png";
    private static TextureAtlas.AtlasRegion changed_bg1 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path1), 0, 0, 1920, 1080);
    @SpirePostfixPatch
    public static void Postfix(TheBeyondScene ___instance, SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (AbstractDungeon.getMonsters() != null && AbstractDungeon.getMonsters().monsters != null) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo != null && (mo instanceof YangJian)) {
                    if (Settings.isFourByThree) {
                        sb.draw(changed_bg1.getTexture(), changed_bg1.offsetX * Settings.scale, changed_bg1.offsetY * Settings.yScale, 0.0F, 0.0F, (float) changed_bg1.packedWidth, (float) changed_bg1.packedHeight, Settings.scale, Settings.yScale, 0.0F, changed_bg1.getRegionX(), changed_bg1.getRegionY(), changed_bg1.getRegionWidth(), changed_bg1.getRegionHeight(), false, false);
                    } else if (Settings.isLetterbox) {
                        sb.draw(changed_bg1.getTexture(), changed_bg1.offsetX * Settings.xScale, changed_bg1.offsetY * Settings.xScale, 0.0F, 0.0F, (float) changed_bg1.packedWidth, (float) changed_bg1.packedHeight, Settings.xScale, Settings.xScale, 0.0F, changed_bg1.getRegionX(), changed_bg1.getRegionY(), changed_bg1.getRegionWidth(), changed_bg1.getRegionHeight(), false, false);
                    } else {
                        sb.draw(changed_bg1.getTexture(), changed_bg1.offsetX * Settings.scale, changed_bg1.offsetY * Settings.scale, 0.0F, 0.0F, (float) changed_bg1.packedWidth, (float) changed_bg1.packedHeight, Settings.scale, Settings.scale, 0.0F, changed_bg1.getRegionX(), changed_bg1.getRegionY(), changed_bg1.getRegionWidth(), changed_bg1.getRegionHeight(), false, false);
                    }
                    break;
                }

            }
        }

    }
}
