package zakoi.livewallpaper.religious.shivsanker;
import aurelienribon.tweenengine.TweenAccessor;

public class ohmTileAccessor implements TweenAccessor<ohmTile> {

    // The following lines define the different possible tween types.
    // It's up to you to define what you need :-)

    public static final int POS_XY 	= 1;
    public static final	int POS_Z	= 2;
    
    // TweenAccessor implementation

    @Override
    public int getValues(ohmTile target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POS_XY: 	returnValues[0] = target.getx();
            				returnValues[1] = target.gety();
            				return 1;
            case POS_Z:		returnValues[0] = target.getTweenVal();
            				return 1;
            default: assert false; return -1;
        }
    }
    
    @Override
    public void setValues(ohmTile target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POS_XY: 	target.setx(newValues[0]);
            				target.sety(newValues[1]);
            		 		break;
            case POS_Z:		target.setTweenVal(newValues[0]);
							break;
            default: assert false; break;
        }
    }
}