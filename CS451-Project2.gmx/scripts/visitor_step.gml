///visitor_step()

if (point_distance(x, y, gotox, gotoy) < 15){
    x = gotox;
    y = gotoy;
} else {
    move_towards_point(gotox, gotoy, 12);
}
