package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import monsters.act1.boss.SharkDemonKing;

public class SharkDamageAction extends AbstractGameAction {
    private DamageInfo info;
    public AbstractCreature master;

    public SharkDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        this.tickDuration();
        if (this.isDone) {
            this.target.damage(this.info);
            if (this.target.lastDamageTaken > 0) {
                if (AbstractDungeon.getMonsters() != null && AbstractDungeon.getMonsters().monsters != null) {
                    for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                        if (mo != null && (mo instanceof SharkDemonKing)) {
                            this.master = AbstractDungeon.getMonsters().getMonster(SharkDemonKing.ID);
                            this.addToTop(new HealAction(this.master, this.master, this.target.lastDamageTaken));
                            this.addToTop(new WaitAction(0.1F));
                        }
                    }
                }
                this.addToTop(new HealAction(this.source, this.source, this.target.lastDamageTaken));
                this.addToTop(new WaitAction(0.1F));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

    }
}
