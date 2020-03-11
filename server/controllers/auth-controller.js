var connection = require('../config/config');
bcrypt = require('bcrypt');
const base = {};
const saltRounds = 10;

base.login = async function (req, res) {
    let userData = {email: req.body.email, password: req.body.password};
    connection.query('SELECT * FROM user WHERE email = ?',[userData.email], async function (error, results, fields) {
        if (error) {
            res.json({
                status:false,
                message:'There are some errors with query'
            })
        }else{
            if(results.length >0){
                if (await bcrypt.compare(userData.password, results[0].password)){
                    res.json({
                        status:true,
                        data:results,
                        message: results[0].name + ' you have been successfully authenticated'
                    });
                }else{
                    res.json({
                        status:false,
                        message:"Email and password does not match"
                    });
                }
            }else{
                res.json({
                    status:false,    
                    message:"Email does not exits"
                });
            }
        }   
    });
};

base.register= async function(req,res){
        let password = await bcrypt.hash(req.body.password, saltRounds);
        if (password) {
            req.body.password = password;
        }
    var users={
        name: req.body.name,
        email: req.body.email,
        password: req.body.password
    }
    connection.query('INSERT INTO user SET ?', users, function (error, results, fields) {
      if (error) {
        res.json({
            status:false,
            message:'There are some error with query'
        })
      }else{
          res.json({
            status:true,
            data:results,
            message:'User registered sucessfully'
        })
      }
    });
}

module.exports = base;