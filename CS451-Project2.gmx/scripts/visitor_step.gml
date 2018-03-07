///visitor_step()

if (at_dest){
    if (ds_queue_size(gotox) > 0){
        destx = ds_queue_dequeue(gotox);
        desty = ds_queue_dequeue(gotoy); 
    }
}

var dist = point_distance(x, y, destx, desty);
var spd = max((dist * 12) / 160, 12);

if (dist < spd + 5){
    at_dest = true;
    x = destx;
    y = desty;
    image_alpha = 1;
} else {
    at_dest = false;
    move_towards_point(destx, desty, spd);
    image_alpha = 0.5;
}
