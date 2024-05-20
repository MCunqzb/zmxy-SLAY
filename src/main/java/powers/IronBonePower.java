package powers;

import Helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS;

public class IronBonePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("IronBonePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied = false;

    public IronBonePower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        String path128 = "img/powers/iron_bone84.png";
        String path48 = "img/powers/iron_bone32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;

    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "dreaming_journey_to_the_west:IronBonePower"));
            }else if (this.amount == 1){
                this.addToTop(new ReducePowerAction(this.owner, this.owner, "dreaming_journey_to_the_west:IronBonePower", 1));
            }
            else {
                this.addToTop(new ReducePowerAction(this.owner, this.owner, "dreaming_journey_to_the_west:IronBonePower", this.amount/2));
            }

        }
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if(!type.equals(HP_LOSS)) {
            damage = (float) Math.max((1 - 0.1 * this.amount) * damage, 0.2*damage);
        }
        return damage;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],Math.min(10*this.amount,80));
    }
}
