package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import demoMod.MonkeyKingMod;

public class DodgeTrainingAction extends AbstractGameAction {
    private AbstractCard card;

    public DodgeTrainingAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        this.card.returnToHand = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2
                && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).hasTag(MonkeyKingMod.BOXING);
        this.isDone = true;
    }
}
