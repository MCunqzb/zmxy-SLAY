package patchs;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import monsters.act1.DrakeDemonKing;
import relics.BloodSirenShell;

public class RelicDropLogic {
    public static boolean shouldDropFixedRelic() {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (mo != null && (mo instanceof DrakeDemonKing)) {
                if ( !AbstractDungeon.player.hasRelic(BloodSirenShell.ID)) {
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }
}
