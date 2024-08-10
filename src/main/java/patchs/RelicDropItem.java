package patchs;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import monsters.act1.DrakeDemonKing;
import relics.BloodSirenShell;

public class RelicDropItem {
    public static AbstractRelic getRelicForElite() {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (mo != null && (mo instanceof DrakeDemonKing)) {
                if ( !AbstractDungeon.player.hasRelic(BloodSirenShell.ID)){
                    return new relics.BloodSirenShell();
                }
            }
        }
        return new com.megacrit.cardcrawl.relics.Circlet();
    }
}
