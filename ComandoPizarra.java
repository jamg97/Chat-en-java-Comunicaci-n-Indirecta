/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

/**
 *
 * @author gadol
 */


import org.jgroups.util.Streamable;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Encapsulates information about a draw command.
 * Used by the {@link Draw} and other demos.
 *
 */
public class ComandoPizarra implements Streamable {
    static final byte DRAW=1;
    static final byte CLEAR=2;
    byte mode;
    int x;
    int y;
    int rgb;

    public ComandoPizarra() { // needed for streamable
    }

    ComandoPizarra(byte mode) {
        this.mode=mode;
    }

    ComandoPizarra(byte mode, int x, int y, int rgb) {
        this.mode=mode;
        this.x=x;
        this.y=y;
        this.rgb=rgb;
    }


    @Override
    public void writeTo(DataOutput out) throws Exception {
        out.writeByte(mode);
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(rgb);
    }

    @Override
    public void readFrom(DataInput in) throws Exception {
        mode=in.readByte();
        x=in.readInt();
        y=in.readInt();
        rgb=in.readInt();
    }


    @Override
    public String toString() {
        StringBuilder ret=new StringBuilder();
        switch(mode) {
            case DRAW: ret.append("DRAW(" + x + ", " + y + ") [" + rgb + "]");
                break;
            case CLEAR: ret.append("CLEAR");
                break;
            default:
                return "<undefined>";
        }
        return ret.toString();
    }

}
