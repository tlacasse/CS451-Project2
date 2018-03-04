///update_visitor(id, items, desired_items, status)

var obj = ds_map_find_value(Client.PEOPLE, argument0);

if (is_undefined(obj)){
    _Visitor(argument0, argument1, argument2, argument3);
} else {
    obj.items = argument1;
    obj.desired = argument2;
    obj.status = argument3;
    obj.gotox = Client.ITEMPOSITIONS[obj.items, 0] + obj.disp_x;
    obj.gotoy = Client.ITEMPOSITIONS[obj.items, 1] + obj.disp_y;
}
