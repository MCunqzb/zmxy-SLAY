package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class InHighSpiritsAction extends AbstractGameAction {
    private AbstractPlayer p;

    public InHighSpiritsAction() {
        this.actionType = ActionType.WAIT;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.p.hasPower("Vigor")) {
            int strAmt = this.p.getPower("Vigor").amount;
            this.addToTop(new ApplyPowerAction(this.p, this.p, new VigorPower(this.p, strAmt), strAmt));
        }

        this.tickDuration();
    }
}
