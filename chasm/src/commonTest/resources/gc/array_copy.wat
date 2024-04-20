(module
  (type $arr8 (array i8))
  (type $arr8_mut (array (mut i8)))

  (global $g_arr8 (ref $arr8) (array.new $arr8 (i32.const 10) (i32.const 12)))
  (global $g_arr8_mut (mut (ref $arr8_mut)) (array.new_default $arr8_mut (i32.const 12)))

  (data $d1 "abcdefghijkl")

  (func (export "array_get_nth") (param $1 i32) (result i32)
    (array.get_u $arr8_mut (global.get $g_arr8_mut) (local.get $1))
  )

  (func (export "array_copy-null-left")
    (array.copy $arr8_mut $arr8 (ref.null $arr8_mut) (i32.const 0) (global.get $g_arr8) (i32.const 0) (i32.const 0))
  )

  (func (export "array_copy-null-right")
    (array.copy $arr8_mut $arr8 (global.get $g_arr8_mut) (i32.const 0) (ref.null $arr8) (i32.const 0) (i32.const 0))
  )

  (func (export "array_copy") (param $1 i32) (param $2 i32) (param $3 i32)
    (array.copy $arr8_mut $arr8 (global.get $g_arr8_mut) (local.get $1) (global.get $g_arr8) (local.get $2) (local.get $3))
  )

  (func (export "array_copy_overlap_test-1")
    (local $1 (ref $arr8_mut))
    (array.new_data $arr8_mut $d1 (i32.const 0) (i32.const 12))
    (local.set $1)
    (array.copy $arr8_mut $arr8_mut (local.get $1) (i32.const 1) (local.get $1) (i32.const 0) (i32.const 11))
    (global.set $g_arr8_mut (local.get $1))
  )

  (func (export "array_copy_overlap_test-2")
    (local $1 (ref $arr8_mut))
    (array.new_data $arr8_mut $d1 (i32.const 0) (i32.const 12))
    (local.set $1)
    (array.copy $arr8_mut $arr8_mut (local.get $1) (i32.const 0) (local.get $1) (i32.const 1) (i32.const 11))
    (global.set $g_arr8_mut (local.get $1))
  )
)
