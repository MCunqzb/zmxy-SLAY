package powers;

import Helpers.ModHelper;
import actions.ScorchLoseHpAction;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class ScorchPower extends AbstractPower implements HealthBarRenderPower, CloneablePowerInterface {
    public static final String POWER_ID = ModHelper.MakePath("ScorchPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private AbstractCreature source;

    public ScorchPower(AbstractCreature owner,AbstractCreature source, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.type = PowerType.DEBUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;
        // 添加一大一小两张能力图
        String path128 = "img/powers/scorch84.png";
        String path48 = "img/powers/scorch32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
        this.isTurnBased = true;

    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + Math.min((this.amount)*2.5, 50 )+ DESCRIPTIONS[1]+ DESCRIPTIONS[2] + (this.amount) + DESCRIPTIONS[3];
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            this.addToTop(new ScorchLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.FIRE));
        }
        this.updateDescription();
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            if (this.owner.isPlayer && AbstractDungeon.player.hasRelic("Odd Mushroom")) {
                return damage *(0.025F * this.amount+1);
            } else {
                return this.owner != null && !this.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Frog") ? damage * Math.min((0.0375F * this.amount+1),1.75f) : damage * Math.min((0.025F * this.amount+1),1.5f);
            }
        } else {
            return damage;
        }
    }

    public Color getColor() {
        return Color.YELLOW;
    }

    public int getHealthBarAmount() {
        if(this.owner.hasPower(VulnerablePower.POWER_ID)){
            return 2*this.amount;
        }
        else
            return this.amount;

    }

    @Override
    public AbstractPower makeCopy() {
        return new ScorchPower(this.owner, this.source, this.amount);
    }
}
