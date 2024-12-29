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
import com.megacrit.cardcrawl.scenes.TheCityScene;
import monsters.act2.Judge;
import monsters.act2.Lamp;
import monsters.act2.QinGuangKing;
import monsters.act2.boss.WheelTurningKing;


@SpirePatch(clz = TheCityScene.class, method = "renderCombatRoomBg")
public class TheCitySceneBGPatch {
    private static String path1 = "img/scene/zmxy2-4.png";
    private static String path2 = "img/scene/zmxy2-2.png";
    private static String path3 = "img/scene/zmxy2-2-2.png";
    private static String path4 = "img/scene/zmxy2-2-3.png";
    private static String path5 = "img/scene/zmxy2-3-1.png";
    private static String path6 = "img/scene/zmxy2-3-2.png";
    private static String path7 = "img/scene/zmxy3-2-xt.png";
    private static String path8 = "img/scene/zmxy3-2-cj.png";
    private static String bottom = "img/scene/bottom_scene.jpg";
    private static TextureAtlas.AtlasRegion changed_bg1 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path1), 0, 0, 1920, 1080);
    private static TextureAtlas.AtlasRegion changed_bg2 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path2), 0, 0, 1920, 1080);
    private static TextureAtlas.AtlasRegion changed_bg3 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path3), 0, 0, 1920, 1080);
    private static TextureAtlas.AtlasRegion changed_bg4 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path4), 0, 0, 1920, 1080);
    @SpirePostfixPatch
    public static void Postfix(TheCityScene __instance, SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (AbstractDungeon.getMonsters() != null && AbstractDungeon.getMonsters().monsters != null) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo != null && (mo instanceof WheelTurningKing)) {
                    if (Settings.isFourByThree) {
                        sb.draw(changed_bg1.getTexture(), changed_bg1.offsetX * Settings.scale, changed_bg1.offsetY * Settings.yScale, 0.0F, 0.0F, (float) changed_bg1.packedWidth, (float) changed_bg1.packedHeight, Settings.scale, Settings.yScale, 0.0F, changed_bg1.getRegionX(), changed_bg1.getRegionY(), changed_bg1.getRegionWidth(), changed_bg1.getRegionHeight(), false, false);
                    } else if (Settings.isLetterbox) {
                        sb.draw(changed_bg1.getTexture(), changed_bg1.offsetX * Settings.xScale, changed_bg1.offsetY * Settings.xScale, 0.0F, 0.0F, (float) changed_bg1.packedWidth, (float) changed_bg1.packedHeight, Settings.xScale, Settings.xScale, 0.0F, changed_bg1.getRegionX(), changed_bg1.getRegionY(), changed_bg1.getRegionWidth(), changed_bg1.getRegionHeight(), false, false);
                    } else {
                        sb.draw(changed_bg1.getTexture(), changed_bg1.offsetX * Settings.scale, changed_bg1.offsetY * Settings.scale, 0.0F, 0.0F, (float) changed_bg1.packedWidth, (float) changed_bg1.packedHeight, Settings.scale, Settings.scale, 0.0F, changed_bg1.getRegionX(), changed_bg1.getRegionY(), changed_bg1.getRegionWidth(), changed_bg1.getRegionHeight(), false, false);
                    }
                    break;
                }else if (mo != null && (mo instanceof QinGuangKing)) {
                    if (Settings.isFourByThree) {
                        sb.draw(changed_bg2.getTexture(), changed_bg2.offsetX * Settings.scale, changed_bg2.offsetY * Settings.yScale, 0.0F, 0.0F, (float) changed_bg2.packedWidth, (float) changed_bg2.packedHeight, Settings.scale, Settings.yScale, 0.0F, changed_bg2.getRegionX(), changed_bg2.getRegionY(), changed_bg2.getRegionWidth(), changed_bg2.getRegionHeight(), false, false);
                    } else if (Settings.isLetterbox) {
                        sb.draw(changed_bg2.getTexture(), changed_bg2.offsetX * Settings.xScale, changed_bg2.offsetY * Settings.xScale, 0.0F, 0.0F, (float) changed_bg2.packedWidth, (float) changed_bg2.packedHeight, Settings.xScale, Settings.xScale, 0.0F, changed_bg2.getRegionX(), changed_bg2.getRegionY(), changed_bg2.getRegionWidth(), changed_bg2.getRegionHeight(), false, false);
                    } else {
                        sb.draw(changed_bg2.getTexture(), changed_bg2.offsetX * Settings.scale, changed_bg2.offsetY * Settings.scale, 0.0F, 0.0F, (float) changed_bg2.packedWidth, (float) changed_bg2.packedHeight, Settings.scale, Settings.scale, 0.0F, changed_bg2.getRegionX(), changed_bg2.getRegionY(), changed_bg2.getRegionWidth(), changed_bg2.getRegionHeight(), false, false);
                    }
                    break;
                }else if (mo != null && (mo instanceof Judge) || (mo instanceof Lamp)) {
                    if (Settings.isFourByThree) {
                        sb.draw(changed_bg3.getTexture(), changed_bg3.offsetX * Settings.scale, changed_bg3.offsetY * Settings.yScale, 0.0F, 0.0F, (float) changed_bg3.packedWidth, (float) changed_bg3.packedHeight, Settings.scale, Settings.yScale, 0.0F, changed_bg3.getRegionX(), changed_bg3.getRegionY(), changed_bg3.getRegionWidth(), changed_bg3.getRegionHeight(), false, false);
                    } else if (Settings.isLetterbox) {
                        sb.draw(changed_bg3.getTexture(), changed_bg3.offsetX * Settings.xScale, changed_bg3.offsetY * Settings.xScale, 0.0F, 0.0F, (float) changed_bg3.packedWidth, (float) changed_bg3.packedHeight, Settings.xScale, Settings.xScale, 0.0F, changed_bg3.getRegionX(), changed_bg3.getRegionY(), changed_bg3.getRegionWidth(), changed_bg3.getRegionHeight(), false, false);
                    } else {
                        sb.draw(changed_bg3.getTexture(), changed_bg3.offsetX * Settings.scale, changed_bg3.offsetY * Settings.scale, 0.0F, 0.0F, (float) changed_bg3.packedWidth, (float) changed_bg3.packedHeight, Settings.scale, Settings.scale, 0.0F, changed_bg3.getRegionX(), changed_bg3.getRegionY(), changed_bg3.getRegionWidth(), changed_bg3.getRegionHeight(), false, false);
                    }
                    break;
                }
            }
        }

    }
}
