package world.creature;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Cell;
import world.item.Item;
import world.item.ItemKind;
import world.tile.Tile;
import world.tile.TileKind;

public class PlayerAI extends CreatureAI{
    private List<String> messages;

    public PlayerAI(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }  

    @Override
    public void onEnter(int x,int y,Tile tile){
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
            if(tile.hasItem()){
                creature.visitItem(tile.item());
            }
        }
        else if(tile.isDoor()){
            if(tile.isDoorYellow()){
                if(tryUseKey(ItemKind.KEY_YELLOW))creature.floor().changeTile(x, y, TileKind.FLOOR);
            }
            else if(tile.isDoorBlue()){
                if(tryUseKey(ItemKind.KEY_BLUE))creature.floor().changeTile(x, y, TileKind.FLOOR);
            }
            else if(tile.isDoorRed()){
                if(tryUseKey(ItemKind.KEY_RED))creature.floor().changeTile(x, y, TileKind.FLOOR);
            }
            
        }
        else if(tile.isUpstair()){
            creature.goUpstair();
        }
        else if(tile.isDownstair()){
            creature.goDownstair();
        }
    }

    private boolean tryUseKey(ItemKind itemKind){

        for(Item item:creature.items()){
            if(item.kind()==itemKind){
                creature.items().remove(item);
                return true;
            }
        }
        return false;
    }


    //private 

    @Override
    public void onNotify(String message) {
        this.messages.add(message);
    }

    @Override
    public Map<String,String> status(){
        Map<String,String> status=new LinkedHashMap<>();
        status.put("Player", creature.glyph()+"");
        status.put("HP", creature.hp()+"");
        status.put("Attack", creature.attackValue()+"");
        status.put("Defence", creature.defenseValue()+"");
        // String items="";
        // for(Item item: creature.items()){
        //     items+=(item.glyph()+" ");
        // }
        // status.put("Item", items);

        return status;
    }

}