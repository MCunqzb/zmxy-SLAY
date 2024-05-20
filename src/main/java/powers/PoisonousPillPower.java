package powers;

import Helpers.ModHelper;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PoisonousPillPower extends AbstractPower implements HealthBarRenderPower, CloneablePowerInterface {
    public static final String POWER_ID = ModHelper.MakePath("PoisonousPillPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int hp_lose = 60;
    private boolean attacked = false;

    public PoisonousPillPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        String path128 = "img/powers/poisonous_pill84.png";
        String path48 = "img/powers/poisonous_pill32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;
    }

    public void atEndOfRound() {
        if (this.attacked) {
            this.addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.hp_lose, DamageInfo.DamageType.HP_LOSS)));
        }
        this.attacked=false;
    }

    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS) {
            this.attacked=true;
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.hp_lose);
    }

    public Color getColor() {
        return Color.GREEN;
    }

    public int getHealthBarAmount() {
        if(this.attacked){
            return this.hp_lose;
        }
        else
            return 0;
    }

    @Override
    public AbstractPower makeCopy() {
        return new PoisonousPillPower(this.owner);
    }
}