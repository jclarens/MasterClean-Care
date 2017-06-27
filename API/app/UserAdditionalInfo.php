<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class UserAdditionalInfo extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'userId',
        'infoId',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [ ];

    /**
     * Get the user record associated with the userAdditionalInfo.
     */
    public function user()
    {
        return $this->belongsTo('App\User', 'userId');
    }

    /**
     * Get the additionalInfo record associated with the userAdditionalInfo.
     */
    public function additionalInfo()
    {
        return $this->belongsTo('App\AdditionalInfo', 'infoId');
    }
}
