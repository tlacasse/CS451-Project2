///update_visitor(id, items, desired_items, status)

var obj = ds_map_find_value(Client.PEOPLE, argument0);

if (is_undefined(obj)){
    _Visitor(argument0, argument1, argument2, argument3);
} else {
    obj.items = argument1;
    obj.desired = argument2;
    obj.status = argument3;
    if (obj.canqueue){
        if (obj.status == VisitorStatus.DONE){
            change_goto(obj, room_width * 1.2, ds_queue_tail(obj.gotoy));
            if (!obj.goleave){
                obj.goleave = true;
                count_checkout--;
            }
        }
    } else {
        //force to go to every item
        if (obj.preitems == obj.desired){
            obj.canqueue = true;
            count_shopping--;
            count_queue++
        }
        obj.status = VisitorStatus.SHOPPING;
        var _gotox = Client.ITEMPOSITIONS[obj.preitems, 0] + obj.disp_x;
        var _gotoy = Client.ITEMPOSITIONS[obj.preitems, 1] + obj.disp_y;
        if (obj.preitems < obj.items){
            obj.preitems++;
        }
        change_goto(obj, _gotox, _gotoy);
    }
}
