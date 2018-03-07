///visitor_step()

var dist = point_distance(x, y, gotox, gotoy);
var spd = max((dist * 12) / 160, 12);

if (dist < spd + 5){
    x = gotox;
    y = gotoy;
    image_alpha = 1;
} else {
    move_towards_point(gotox, gotoy, spd);
    image_alpha = 0.5;
}
