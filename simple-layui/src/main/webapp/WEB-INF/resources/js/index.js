/**
 * 
 */
layui.use(['layer', 'element','form'], function(){
  var element = layui.element();
  
  
  var form = layui.form();
  form.on('submit(formRegister)', function(data){
    layer.msg(JSON.stringify(data.field));
    return false;
  });
  
  exports('register', {});
});