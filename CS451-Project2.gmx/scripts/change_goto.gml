///change_goto(visitor, x, y)

var vv = argument0;
var vx = argument1;
var vy = argument2;

if (ds_queue_tail(vv.gotox) != vx || ds_queue_tail(vv.gotoy) != vy){
    ds_queue_enqueue(vv.gotox, vx);
    ds_queue_enqueue(vv.gotoy, vy);
}
