package powers;

import Helpers.ModHelper;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BloodThirstyPower extends AbstractPower implements HealthBarRenderPower {
    public static final String POWER_ID = ModHelper.MakePath("BloodThirstyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BloodThirstyPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        if (this.amount >= 9) {
            this.amount = 9;
        }
        // 添加一大一小两张能力图
        String path128 = "img/powers/blood_thirsty84.png";
        String path48 = "img/powers/blood_thirsty32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
        this.isTurnBased = true;
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount/3 >= 1 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            //BaseMod.logger.info("AMT"+this.amount/3*damageAmount);
            this.addToTop(new HealAction(this.owner, this.owner, (int)(damageAmount*this.amount*0.3334) ));
        }

    }

    public void atStartOfTurn() {
        this.addToTop(new LoseHPAction(this.owner, this.owner, 3*this.amount));
    }


    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public int getHealthBarAmount() {
        return 3*this.amount;
    }

    @Override
    public Color getColor() {
         return Color.ORANGE;
    }
}
