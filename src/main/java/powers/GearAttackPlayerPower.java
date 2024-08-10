package powers;

import Helpers.ModHelper;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import java.util.Iterator;

public class GearAttackPlayerPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("GearAttackPlayerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int damages;


    public GearAttackPlayerPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        String path128 = "img/powers/gear_attack84.png";;
        String path48 = "img/powers/gear_attack32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;

    }


    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner.hasPower(GearAttackPower.POWER_ID)){
            if (this.owner.flipHorizontal == info.owner.flipHorizontal){
                BaseMod.logger.info("player flipHorizontal :" + this.owner.flipHorizontal );
                BaseMod.logger.info("monster flipHorizontal :" + info.owner.flipHorizontal );
                damageAmount = (int)(damageAmount * 0.5F);
            }else {
                damageAmount = (int)(damageAmount * 1.5F);
            }
        }
        return damageAmount;
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        this.damages = 0;
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(true) {
            if (!var1.hasNext()) {
                break;
            }
            AbstractMonster m = (AbstractMonster)var1.next();
            if (!m.isDead && !m.isDying) {
                if (m.hasPower(GearAttackPower.POWER_ID)) {
                    if (this.owner.flipHorizontal == m.flipHorizontal) {
                        this.damages = this.damages + (int) (m.getIntentBaseDmg() * 0.5);
                    } else {
                        this.damages = this.damages + (int) (m.getIntentBaseDmg() * 1.5);
                    }
                }else {
                    this.damages = this.damages + (int) (m.getIntentBaseDmg() * 1);
                }
                this.damages = Math.max(0, this.damages);
                BaseMod.logger.info("Gear damage:" + this.damages);
            }
        }
        this.updateDescription();
    }




    public void updateDescription() {
        this.description =  String.format(DESCRIPTIONS[0],this.damages);
    }

}
