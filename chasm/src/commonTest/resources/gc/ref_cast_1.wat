(module
  (type $ft (func))
  (type $st (struct))
  (type $at (array i8))

  (table 10 anyref)

  (elem declare func $f)
  (func $f)

  (func (export "init") (param $x externref)
    (table.set (i32.const 0) (ref.null any))
    (table.set (i32.const 1) (ref.i31 (i32.const 7)))
    (table.set (i32.const 2) (struct.new_default $st))
    (table.set (i32.const 3) (array.new_default $at (i32.const 0)))
    (table.set (i32.const 4) (any.convert_extern (local.get $x)))
    (table.set (i32.const 5) (ref.null i31))
    (table.set (i32.const 6) (ref.null struct))
    (table.set (i32.const 7) (ref.null none))
  )

  (func (export "ref_cast_non_null") (param $i i32)
    (drop (ref.as_non_null (table.get (local.get $i))))
    (drop (ref.cast (ref null any) (table.get (local.get $i))))
  )
  (func (export "ref_cast_null") (param $i i32)
    (drop (ref.cast anyref (table.get (local.get $i))))
    (drop (ref.cast structref (table.get (local.get $i))))
    (drop (ref.cast arrayref (table.get (local.get $i))))
    (drop (ref.cast i31ref (table.get (local.get $i))))
    (drop (ref.cast nullref (table.get (local.get $i))))
  )
  (func (export "ref_cast_i31") (param $i i32)
    (drop (ref.cast (ref i31) (table.get (local.get $i))))
    (drop (ref.cast i31ref (table.get (local.get $i))))
  )
  (func (export "ref_cast_struct") (param $i i32)
    (drop (ref.cast (ref struct) (table.get (local.get $i))))
    (drop (ref.cast structref (table.get (local.get $i))))
  )
  (func (export "ref_cast_array") (param $i i32)
    (drop (ref.cast (ref array) (table.get (local.get $i))))
    (drop (ref.cast arrayref (table.get (local.get $i))))
  )
)
