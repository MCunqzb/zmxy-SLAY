package patchs;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import monsters.act4.*;

import java.util.Iterator;

@SpirePatch(clz = TheEndingScene.class, method = "renderCombatRoomBg")
public class TheEndingBGPatch {

    private static String path = "img/scene/zmxy4.png";
    private static TextureAtlas.AtlasRegion changed_bg =new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path),0,0,1920,1080) ;
    @SpirePostfixPatch
    public static void Postfix(TheEndingScene __instance, SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (AbstractDungeon.getMonsters() != null && AbstractDungeon.getMonsters().monsters != null) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo != null && mo instanceof TheSovereignofTheTongtianSect) {
                    if (Settings.isFourByThree) {
                        sb.draw(changed_bg.getTexture(), changed_bg.offsetX * Settings.scale, changed_bg.offsetY * Settings.yScale, 0.0F, 0.0F, (float) changed_bg.packedWidth, (float) changed_bg.packedHeight, Settings.scale, Settings.yScale, 0.0F, changed_bg.getRegionX(), changed_bg.getRegionY(), changed_bg.getRegionWidth(), changed_bg.getRegionHeight(), false, false);
                    } else if (Settings.isLetterbox) {
                        sb.draw(changed_bg.getTexture(), changed_bg.offsetX * Settings.xScale, changed_bg.offsetY * Settings.xScale, 0.0F, 0.0F, (float) changed_bg.packedWidth, (float) changed_bg.packedHeight, Settings.xScale, Settings.xScale, 0.0F, changed_bg.getRegionX(), changed_bg.getRegionY(), changed_bg.getRegionWidth(), changed_bg.getRegionHeight(), false, false);
                    } else {
                        sb.draw(changed_bg.getTexture(), changed_bg.offsetX * Settings.scale, changed_bg.offsetY * Settings.scale, 0.0F, 0.0F, (float) changed_bg.packedWidth, (float) changed_bg.packedHeight, Settings.scale, Settings.scale, 0.0F, changed_bg.getRegionX(), changed_bg.getRegionY(), changed_bg.getRegionWidth(), changed_bg.getRegionHeight(), false, false);
                    }
                    break;
                }
            }
        }





    }
}
