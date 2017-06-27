<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Places extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'name',
        'parent',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [ ];

    /**
     * Get the userPlaces record associated with the places.
     */
    public function userPlaces()
    {
        return $this->hasMany('App\UserPlaces');
    }
}
