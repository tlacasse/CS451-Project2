///visitor_step()

var destx = ds_queue_head(gotox);
var desty = ds_queue_head(gotoy);

var dist = point_distance(x, y, destx, desty);
var spd = max((dist * 12) / 220, 10);

if (dist < spd + 5){
    at_dest = true;
    x = destx;
    y = desty;
    image_alpha = 1;
    if (ds_queue_size(gotox) > 1){
        ds_queue_dequeue(gotox);
        ds_queue_dequeue(gotoy);
    }
} else {
    at_dest = false;
    move_towards_point(destx, desty, spd);
    image_alpha = 0.5;
}
