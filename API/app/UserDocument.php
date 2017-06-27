<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class UserDocument extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'userId',
        'documentName',
        'documentLocation',
        'documentType',
        'experience',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [ ];

    /**
     * Get the user record associated with the userDocument.
     */
    public function user()
    {
        return $this->belongsTo('App\User', 'userId');
    }
}
